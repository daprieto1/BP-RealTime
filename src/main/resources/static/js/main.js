function appendRider(rider) {
    var $riders = $('#riders');
    $riders.html('');

    var $rider = $(`
        <tr>
            <td>${rider.id}</td>
            <td>${rider.name}</td>
            <td>${rider.avgSpeed}</td>
            <td>${rider.totalKms}</td>
        </tr>        
    `);
    $riders.append($rider);
}

function getRidersPolling() {
    $.get('/riders').done(riders => riders.forEach(appendRider));
    setTimeout(() => {
        getRidersPolling()
    }, 5000)
}

async function getRidersLongPolling() {
    let response = await fetch("/riders/long");

    if (response.status == 408) {
        await getRidersLongPolling();
    } else if (response.status != 200) {
        console.log(response.statusText);
        await new Promise(resolve => setTimeout(resolve, 1000));
        await getRidersLongPolling();
    } else {
        let riders = JSON.parse(await response.text());
        riders.forEach(appendRider)
        await getRidersLongPolling();
    }
}

//getRidersPolling();

$.get('/riders').done(riders => riders.forEach(appendRider));
getRidersLongPolling();
