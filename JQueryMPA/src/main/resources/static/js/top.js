function completeTask(e) {
    const task = $(e.target).closest('tr').data('task');

    task.isCompleted = true;

    $.ajax(`/api/tasks/${task.id}`, {
        type: 'PUT',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(task)
    }).done(response => {
        const $buttonCell = $(`#task_table tbody tr[data-task-id="${response.id}"] td:nth-child(3)`);
        if (response.isCompleted) {
            $buttonCell.text('完了済み');
        } else {
            $buttonCell.append(
                $(`<button type="button" onClick="completeTask(event)">完了</button>`)
            ).append(
                $(`<button type="button" onClick="deleteTask(${response.id})">削除</button>`)
            );
        }
    });
}

function deleteTask(taskId) {
    $.ajax(`/api/tasks/${taskId}`, { type: 'DELETE' }).done(() => {
        $(`#task_table tbody tr[data-task-id="${taskId}"]`).remove();
    });
}

function loadTasks() {
    const $tbody = $('#task_table tbody');

    $.get(`/api/tasks`).done(tasks => {
        for (let task of tasks) {
            const $tr = createRow(task).data('task', task);
            $tbody.append($tr);
        }
    });
}

function createRow(task) {
    let $buttonCell = $('<td></td>')
    if (task.isCompleted) {
        $buttonCell.text('完了済み');
    } else {
        $buttonCell.append(
            $(`<button type="button" onClick="completeTask(event)">完了</button>`)
        ).append(
            $(`<button type="button" onClick="deleteTask(${task.id})">削除</button>`)
        );
    }

    return $(`<tr data-task-id="${task.id}"></tr>`)
        .append(`<td>${task.id}</td>`)
        .append(`<td>${task.subject}</td>`)
        .append($buttonCell);
}

function createTask(e) {
    e.preventDefault();

    const form = e.target;

    const request = {
        subject: new FormData(form).get('subject'),
        isCompleted: false
    };

    $.ajax('/api/tasks', {
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(request)
    }).done(() => form.reset()).done(response => {
        const $tr = createRow(response).data('task', response);
        $('#task_table tbody').append($tr);
    });
}

$(function() {
    $('#create_task_form').submit(createTask);

    loadTasks();
});