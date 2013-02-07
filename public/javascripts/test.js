$(function() {
    console.debug('Hello, this is test.js');

    $('#button').click(function() {
        console.debug('やふう');

        $().toastmessage('showToast', {
            text: 'Some information for you ...',
            type: 'success',
            close: function() { alert('Hello'); }
        });

    });
});

