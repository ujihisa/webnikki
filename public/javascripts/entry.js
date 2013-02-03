$(function() {
    $('#submit').submit(function() {
        $.ajax({
            type: 'POST',
            url: '/entry',
            data: 'post_id=' + $('#post_id').val() + '&uname=' + $('#uname').val() +'&content=' + $('#content').val(),
        }).done(function(data) {
            alert('コメント投稿に成功しました!');
            // TODO: コメントを同ページに追記
        }).fail(function(data) {
            alert('コメント投稿に失敗しちゃったみたい...');
        }).always(function(data) {});

        return false;
    });
});
