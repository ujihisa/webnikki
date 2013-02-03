$(function() {
    $('#submit').submit(function() {
        $.ajax({
            type: 'POST',
            url: '/post',
            data: 'token=' + $('#token').val() + '&title=' + $('#title').val() + '&content=' + $('#content').val(),
        }).done(function(data) {
            alert('記事投稿に成功しました!');
            // TODO: 記事トップへ飛ばすか、もしくは記事トップへのリンクを表示する。あとダイアログは自動的に消えるべきだと思う。
        }).fail(function(data) {
            alert('記事投稿に失敗しちゃったみたい...');
        }).always(function(data) {});

        return false;
    });
});
