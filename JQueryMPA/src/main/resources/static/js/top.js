function completeTask(taskId) {
    const task = $('#task_table').data('tasks').find(t => t.id == taskId);

    task.isCompleted = true;

    $.ajax(`/api/tasks/${task.id}`, {
        type: 'PUT',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(task)
    }).done(refreshTable);
}

function deleteTask(taskId) {
     $.ajax('/api/tasks/' + taskId, { type: 'DELETE' }).done(refreshTable);
}

function refreshTable() {
    $('#create_task_form input').val('');
    const $tbody = $('#task_table tbody').empty();

    $.get('/api/tasks', function(tasks) {
        $('#task_table').data('tasks', tasks);

        for (let task of tasks) {
            let $buttonCell = $('<td></td>')
            if (task.isCompleted) {
                $buttonCell.text('完了済み');
            } else {
                $buttonCell.append(
                    $(`<button type="button" onClick="completeTask(${task.id})">完了</button>`)
                ).append(
                    $(`<button type="button" onClick="deleteTask(${task.id})">削除</button>`)
                );
            }

            const $tr = $(`<tr></tr>`)
                .append(`<td>${task.id}</td>`)
                .append(`<td>${task.subject}</td>`)
                .append($buttonCell);

            $tbody.append($tr);
        }
    });
}

function createTask(e) {
    e.preventDefault();

    const request = {
        subject: $(this).children('input[name="subject"]').val(),
        isCompleted: false
    };

    $.ajax('/api/tasks', {
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(request)
    }).done(refreshTable);
}

$(function() {
    $('#create_task_form').submit(createTask);

    refreshTable();
});