package com.byteprogramming.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;

import com.byteprogramming.models.Rider;
import com.byteprogramming.services.IRiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class RiderController {

    private static final Object monitor = new Object();

    @Autowired
    private IRiderService riderService;

    @GetMapping("/riders")
    public List<Rider> getRiders() {
        return riderService.getRiders();
    }

    @PutMapping("/riders/{riderId}")
    public Rider updateRider(@PathVariable UUID riderId, @RequestBody Rider rider) {
        return riderService.updateRider(riderId, rider);
    }

    @GetMapping("/riders/long")
    public DeferredResult<ResponseEntity<?>> getRidersLongPolling() {

        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>(10000l);

        output.onTimeout(new Runnable() {
            @Override
            public void run() {
                output.setErrorResult(
                        ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."));
            }
        });

        ForkJoinPool.commonPool().submit(() -> {
            try {
                synchronized (monitor) {
                    monitor.wait();
                }
            } catch (InterruptedException e) {
            }
            output.setResult(ResponseEntity.ok(riderService.getRiders()));
        });

        return output;
    }

    @PutMapping("/riders/long/{riderId}")
    public Rider updateRiderLongPolling(@PathVariable UUID riderId, @RequestBody Rider rider) {
        Rider updatedRider = riderService.updateRider(riderId, rider);
        synchronized (monitor) {
            monitor.notifyAll();
        }
        return updatedRider;
    }
}
