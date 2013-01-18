$(function() {
    $('#uname').bind('blur', function() {
        $('#uname-info').html('<img src="/assets/images/loading.gif" />');
        $.getJSON('/api/exist/uname/' + $(this).val().trim(), function(json) {
            if (json.exist) {
                $('#uname-info').html('そのユーザー名は既に使われています。');
            } else {
                $('#uname-info').html('');
            }
        });
    });

    $('#password-check').change(function() {
        $('#password').attr('type', $(this).is(':checked') ? 'text' : 'password');
    });
});
