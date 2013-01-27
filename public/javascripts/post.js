$(function() {
    $('#submit').submit(function() {
        $.ajax({
            type: "POST",
            url: "/post",
            data: "token=" + $('#token').val() + "&title=" + $('#title').val() + "&content=" + $('#content').val(),
        }).done(function(data) {
            console.debug('done');
            console.debug(data);
            // TODO: 成功ダイアログを表示して記事トップへ飛ばす
        }).fail(function(data) {
            console.debug('fail');
            console.debug(data);
        }).always(function(data) {});

        return false;
    });
});
