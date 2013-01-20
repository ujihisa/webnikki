// FIXME: "4" should *not* be hard-coded.  use 'data-' property instead
$(function() {
    var minimumPasswordLength = 4;
    var keyToName = {
        'uname': 'ユーザー名',
        'email': 'メールアドレス',
        'password': 'パスワード'
    };
    var errorMessages = {
        'uname': 'その' + keyToName['uname'] + 'は既に使われています。',
        'email': 'その' + keyToName['email'] + 'は既に使われています。',
        'password': keyToName['password'] + 'が短すぎます。'
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

    $('#password').bind('blur', function() {
        $('#password-info').html(($('#password').val().trim().length < minimumPasswordLength) ? errorMessages['password'] : '');
    });

    $('#password-check').change(function() {
        $('#password').attr('type', $(this).is(':checked') ? 'text' : 'password');
    });

    _.each(['uname', 'email', 'password'], function(key) {
        if ($('div#errors > #data-' + key).length) {
            var trId = key + '-tr';
            $('#' + trId).addClass('warning');
            $('#error-messages-list').append('<li>' + keyToName[key] + 'が不正です。</li>');
        }
    });
});
