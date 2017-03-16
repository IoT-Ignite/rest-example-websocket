var REST_API_URL = "https://api.ardich.com/api/v3";

var DEVICE_ID = "b8:27:eb:a2:20:05@iotigniteagent";
var NODE_ID = "Android Things Node";
var LED_SENSOR_ID = "Led";
var BTN_SENSOR_ID = "Button";

function getLedLastStatus() {
    $.ajax({
        url: REST_API_URL + '/device/' + DEVICE_ID + '/sensor-data?nodeId=' + NODE_ID + '&sensorId=' + LED_SENSOR_ID,
        type: 'GET',
        data: {},
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + getAccessToken(),
            'Content-Type': 'application/json'
        }
    }).done(function (result) {
        var value = JSON.parse(result.data.data)[0];
        $('.led-status').text(value).css("color", "#0563AE");
    }).fail(function (error) {
        if(error.status == 401) {
            window.location.href = "./login.html";
            return;
        } else {
            $('#message').html('<label style="color: red;">' + error.responseJSON.message + '</label>');
        }
    });

}

function getBtnLastStatus() {
    $.ajax({
        url: REST_API_URL + '/device/' + DEVICE_ID + '/sensor-data?nodeId=' + NODE_ID + '&sensorId=' + BTN_SENSOR_ID,
        type: 'GET',
        data: {},
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + getAccessToken(),
            'Content-Type': 'application/json'
        }
    }).done(function (result) {
        var value = JSON.parse(result.data.data)[0];
        $('.btn-status').text(value).css("color", "#0563AE");
    }).fail(function (error) {
        if(error.status == 401) {
            window.location.href = "./login.html";
            return;
        } else {
            $('#message').html('<label style="color: red;">' + error.responseJSON.message + '</label>');
        }
    });

}

var ws;

function openSocketConnection() {
    $('.open-socket').hide();
    $('.close-socket').show();
    
    $.ajax({
        url: REST_API_URL + '/subscribe/device/token',
        type: 'GET',
        data: {
            device: DEVICE_ID
        },
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + getAccessToken(),
            'Content-Type': 'application/json'
        }
    }).done(function (result) {
        var subscribeId = result.id;
        var subscribeUrl = result.url;
        var subscribeToken = result.token;

        ws = new WebSocket(subscribeUrl);

        ws.onopen = function () {
            console.log("Web socket connection is opened");
            $('#message').html('<label style="color: green;">Web socket connection is opened</label>');

            var subscribeMessage = new Object();
            subscribeMessage.deviceId = DEVICE_ID;
            subscribeMessage.nodeId = NODE_ID;
            //subscribeMessage.sensorId = LED_SENSOR_ID;
            subscribeMessage.id = subscribeId;
            subscribeMessage.version = "2.0";
            subscribeMessage.token = subscribeToken;

            var subscribe = new Object();
            subscribe.type = "subscribe";
            subscribe.message = subscribeMessage;

            console.log(JSON.stringify(subscribe));

            ws.send(JSON.stringify(subscribe));
        };

        ws.onmessage = function (event) {
            var response = JSON.parse(event.data);
            console.log(JSON.stringify(response));

            if (response.header.type == "Success") {
                console.log("Subscribed");
                $('#message').html('<label style="color: green;">Subscribed</label>');
            } else if (response.header.type == "UnAuthorized") {
                $('#message').html('<label style="color: red;">Subscription Failed</label>');
            } else if (response.header.type == "DeviceInfo") {
                console.log(JSON.stringify(response.body.extras));
                
                var value = response.body.extras.sensorData[0].values[0];
                
                var sensorId = response.body.sensorId;
                
                if(sensorId === LED_SENSOR_ID) {
                    $('.led-status').text(value).css("color", "green");
                } else if(sensorId === BTN_SENSOR_ID) {
                    $('.btn-status').text(value).css("color", "green");
                }
            }
        }

        ws.onclose = function () {
            console.log("Connection is closed");
            $('#message').html('<label style="color: red;">Connection is closed</label>');
            
            $('.led-status').css("color", "#0563AE");
            $('.btn-status').css("color", "#0563AE");
            
            $('.open-socket').show();
            $('.close-socket').hide();
        }
    }).fail(function (error) {
        if(error.status == 401) {
            window.location.href = "./login.html";
            return;
        } else {
            $('#message').html('<label style="color: red;">' + error.responseJSON.message + '</label>');
        }
        
        $('.open-socket').show();
        $('.close-socket').hide();
    });
}

function closeSocketConnection() {
    if (ws) {
        ws.close();
    }
}

function getAccessToken() {
	return window.localStorage.getItem("accessToken");
}
