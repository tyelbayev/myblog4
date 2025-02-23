function openPostForm() {
    document.getElementById("post-form").style.display = "block";
}
function openEditForm() {
    document.getElementById("edit-form").style.display = "block";
}
function deletePost(postId) {
    if (confirm("Вы уверены, что хотите удалить пост?")) {
        fetch(`/myblog/post/${postId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert("Пост успешно удален");
                    window.location.href = "/myblog/";
                } else {
                    alert("Ошибка при удалении поста");
                }
            })
            .catch(error => {
                console.error("Ошибка:", error);
                alert("Ошибка при удалении поста");
            });
    }
}

function likePost(postId) {
    fetch(`/myblog/post/${postId}/like`, {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert("Ошибка при постановке лайка");
            }
        })
        .catch(error => {
            console.error("Ошибка:", error);
            alert("Ошибка при постановке лайка");
        });
}

function addComment(event, postId) {
    event.preventDefault();
    console.log("Добавляем комментарий для поста с ID:", postId);

    const commentText = document.getElementById('comment-text').value.trim();
    fetch(`/myblog/comment`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            postId: postId,
            content: commentText
        })
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert("Ошибка при добавлении комментария");
            }
        })
        .catch(error => {
            console.error("Ошибка:", error);
            alert("Ошибка при добавлении комментария");
        });
}

function editComment(el) {
    let input = document.createElement("input");
    input.type = "text";
    input.value = el.innerText;
    input.onkeydown = function(event) {
        if (event.ctrlKey && event.key === 'Enter') {
            el.innerText = input.value;
            el.parentNode.replaceChild(el, input);
        }
    };
    el.parentNode.replaceChild(input, el);
    input.focus();
}
function deleteComment(commentId) {
    if (confirm("Вы уверены, что хотите удалить комментарий?")) {
        fetch(`/myblog/comment/${commentId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    // alert("Комментарий успешно удален");
                    location.reload();
                } else {
                    alert("Ошибка при удалении комментария");
                }
            })
            .catch(error => {
                console.error("Ошибка:", error);
                alert("Ошибка при удалении комментария");
            });
    }
}
