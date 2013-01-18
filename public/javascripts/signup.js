$(function() {
    var errorMessages = {
        'uname': 'そのユーザー名は既に使われています。',
        'email': 'そのメールアドレスは既に使われています。'
    };

    _.each(['uname', 'email'], function(key) {
        var inputId = '#' + key;
        var spanId = '#' + key + '-info';

        $(inputId).bind('blur', function() {
            $(spanId).html('<img src="/assets/images/loading.gif" />');
            $.getJSON('/api/exist/' + key + '/' + $(this).val().trim(), function(json) {
                $(spanId).html(json.exist ? errorMessages[key] : '');
            });
        });

    });

    $('#password-check').change(function() {
        $('#password').attr('type', $(this).is(':checked') ? 'text' : 'password');
    });
});
