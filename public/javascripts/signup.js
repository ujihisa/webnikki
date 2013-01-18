// FIXME: "4" should *not* be hard-coded.  use 'data-' property instead
$(function() {
    var errorMessages = {
        'uname': 'そのユーザー名は既に使われています。',
        'email': 'そのメールアドレスは既に使われています。',
        'password': 'パスワードが短すぎます。4文字以上のパスワードを入力してください'
    };
    var minimumPasswordLength = 4;

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
});
