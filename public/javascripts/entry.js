$(function() {
    $('#submit').submit(function() {
        $.ajax({
            type: 'POST',
            url: '/entry',
            data: 'post_id=' + $('#post_id').val() + '&uname=' + $('#uname').val() +'&content=' + $('#content').val(),
        }).done(function(data) {
            
        }).fail(function(data) {
            
        }).always(function(data) {});

        console.debug('submit');

        return false;
    });
});
