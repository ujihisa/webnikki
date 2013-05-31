$(function() {
    $('#submit').submit(function() {
        $.ajax({
            type: 'POST',
            url: '/post',
            data: {
                token: $('#token').val(),
                title: $('#post-title').val(),
                content: $('#post-content').val(),
                created_at: $('#created_at').val()
            }
        }).done(function(data) {
            $('#created_at').val(data['created_at']);
            $('#link').html('<a href="' + data['url'] + '">ページを確認する</a>');
            $().toastmessage('showToast', {
                text: '記事' + $('#submit-button').val() + 'に成功しました！',
                position: 'top-center',
                type: 'success',
                stayTime: 3000,
                close: function() {}
            });
            if ($('#submit-button').val() !== '編集') {
                $('#submit-button').val('編集');
            }
        }).fail(function(data) {
            $().toastmessage('showToast', {
                text: '記事' + $('#submit-button').val() + 'に失敗しました...。',
                position: 'top-center',
                type: 'error',
                stayTime: 3000,
                close: function() {}
            });
        });

        return false;
    });

	var myDropzone = new Dropzone("#drag-drop",{
		url: "/image-post",
		parallelUploads: 1,
		maxThumbnailFilesize: 1,
		maxFilesize: 0.5
	});

    myDropzone.on('finished', function(file, json) {
        $('#post-content').val($('#post-content').val() + "![画像](" + json.path + ")\n");
    });
});
