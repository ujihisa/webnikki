$(function() {
    console.debug('Hello, this is test.js');

    $('#button').click(function() {
        console.debug('やふう');

        $().toastmessage('showToast', {
            text: 'Some information for you ...',
            position: 'top-center',
            type: 'success',
            stayTime: 2000,
            // close: function() { alert('Hello'); }
            close: function() { location.href = '/test'; }
        });

    });
});

