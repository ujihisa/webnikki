$(function() {
    $('#submit').submit(function() {
        $.ajax({
            type: 'POST',
            url: '/post',
            data: {
                token: $('#token').val(),
                title: $('#title').val(),
                content: $('#content').val(),
                created_at: $('#created_at').val()
            }
        }).done(function(data) {
            $('#created_at').val(data['created_at']);
            $('#link').html('<a href="' + data['url'] + '">ページを確認する</a>');
            $().toastmessage('showToast', {
                text: '記事投稿に成功しました！',
                position: 'top-center',
                type: 'success',
                stayTime: 3000,
                close: function() {}
            });
        }).fail(function(data) {
            $().toastmessage('showToast', {
                text: '記事投稿に失敗しました...。',
                position: 'top-center',
                type: 'error',
                stayTime: 3000,
                close: function() {}
            });
        });

        return false;
    });
});
