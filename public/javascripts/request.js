$(function() {
    $('#submit').submit(function() {
        if ($('#email').val().indexOf('@') < 0) {
            $('#email-info').html('そのようなメールアドレスは存在しません...');
        } else {
            $('#email-info').html('');
        }

        if ($('#email-info').html() === '') {
            $.ajax({
                type: 'POST',
                url: '/pass/reset-request',
                data: 'token=dummy&password=more-dummy&email='+ $('#email').val().trim()
            }).done(function(data) {
                $().toastmessage('showToast', {
                    text: 'パスワード再設定依頼が完了しました。<br />メールボックスをご確認ください。',
                    position: 'top-center',
                    type: 'success',
                    stayTime: 3000,
                    close: function() {}
                });
            }).fail(function(data) {
                $().toastmessage('showToast', {
                    text: 'パスワード再設定依頼に失敗しました... エラー: ' + (data.message ? data.message : '不明なエラー'),
                    position: 'top-center',
                    type: 'error'
                });
            });
        }

        return false;
    });
});
