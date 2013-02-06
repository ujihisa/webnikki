// FIXME: "4" should *not* be hard-coded.  use 'data-' property instead
$(function() {
    $('#password-check').change(function() {
        $('#password').attr('type', $(this).is(':checked') ? 'text' : 'password');
    });

    $('#submit').submit(function() {
        var api = function(type, value) {
            var defer = $.Deferred();
            $.ajax({
                url: '/api/exist/' + type + '/' + value,
                success: defer.resolve,
                error: defer.reject
            });
            return defer.promise();
        };

        $.when(api('uname', $('#uname').val().trim()),
               api('email', $('#email').val().trim())).
            then(function(uname, email){
                if (uname[0].exist) {
                    $('#uname-info').html('そのユーザー名はすでに使われています。');
                } else if (!$('#uname').val().match(/^[\w\d][\w\d-]*$/)) {
                    $('#uname-info').html('ユーザー名は英数字と"_"と"-"のみ使用可能です。また"-"は先頭には使えません。');
                } else {
                    $('#uname-info').html('');
                }

                if (email[0].exist) {
                    $('#email-info').html('そのメールアドレスはすでに使われています。');
                } else if ($('#email').val().indexOf('@') < 0) {
                    $('#email-info').html('そのようなメールアドレスは存在しません... (´・ω・｀)');
                } else {
                    $('#email-info').html('');
                }

                if ($('#password').val().trim().length < 4) {
                    $('#password-info').html('パスワードは 4 文字以上で入力してください。');
                } else {
                    $('#password-info').html('');
                }

                if ($('#uname-info').html().trim() === '' &&
                    $('#email-info').html().trim() === '' &&
                    $('#password-info').html().trim() === '') {
                    // request

                    console.debug('ここで実際のリクエストしますねー');
                }
        });

        return false;
    });

});
