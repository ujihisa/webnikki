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

                // FIXME: every error check should be done with toast
                if (!$('#terms-of-service').is(':checked')) {
                    $().toastmessage('showToast', {
                        text: '利用規約に同意してください!',
                        position: 'top-center',
                        type: 'error'
                    });
                }

                if ($('#uname-info').html().trim() === '' &&
                    $('#email-info').html().trim() === '' &&
                    $('#password-info').html().trim() === '') {

                    $.ajax({
                        type: 'POST',
                        url: '/signup',
                        data: 'uname=' + $('#uname').val().trim() +
                            '&email=' + $('#email').val().trim() +
                            '&password=' + $('#password').val().trim()
                    }).done(function(data) {
                        $().toastmessage('showToast', {
                            text: 'ユーザー登録に成功しました！<br />トップページに移動します！',
                            position: 'top-center',
                            type: 'success',
                            stayTime: 3000,
                            close: function() { location.href = '/'; }
                        });
                    }).fail(function(data) {
                        $().toastmessage('showToast', {
                            text: 'ユーザー登録に失敗しました... エラー: ' + (data.message ? data.message : '不明なエラー'),
                            position: 'top-center',
                            type: 'error'
                        });

                        return false;
                    });
                }
        });

        return false;
    });
});
