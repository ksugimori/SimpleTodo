/////////////////////////////////////////////////////////////////////////////
// TABLE
/////////////////////////////////////////////////////////////////////////////

function createRow(task) {
    const $tr = $(`<tr data-task-id="${task.id}">
                       <td>${task.id}</td>
                       <td>${task.subject}</td>
                       <td></td>
                   </tr>`);
    $tr.data('task', task);

    updateButtonColumn($tr);

    return $tr;
}

function findRow(taskId) {
    return $(`#task_table tbody tr[data-task-id="${taskId}"]`);
}

function updateButtonColumn($tr) {
    const task = $tr.data('task');
    const $buttonCell = $tr.children('td:nth-child(3)');
    if (task.isCompleted) {
        $buttonCell.text('完了済み');
    } else {
        $buttonCell.append(
            `<button type="button" onClick="completeTask(event)">完了</button>
            <button type="button" onClick="deleteTask(${task.id})">削除</button>`
        );
    }
}

/////////////////////////////////////////////////////////////////////////////
// Event Handler
/////////////////////////////////////////////////////////////////////////////
function completeTask(e) {
    const task = $(e.target).closest('tr').data('task');

    task.isCompleted = true;

    $.ajax(`/api/tasks/${task.id}`, {
        type: 'PUT',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(task)
    })
    .then(response => findRow(response.id))
    .then($tr => updateButtonColumn($tr))
    .catch(e => alert('エラーが発生しました'));
}

function deleteTask(taskId) {
    $.ajax(`/api/tasks/${taskId}`, { type: 'DELETE' })
        .then(() => findRow(taskId).remove())
        .catch(e => alert('エラーが発生しました'));
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
    })
    .then(response => createRow(response))
    .then($tr => $('#task_table tbody').append($tr))
    .then(() => form.reset())
    .catch(e => alert('エラーが発生しました'));
}

/////////////////////////////////////////////////////////////////////////////
// SETUP
/////////////////////////////////////////////////////////////////////////////
$(function() {
    $('#create_task_form').submit(createTask);

    $.get('/api/tasks')
        .then(response => response.map(createRow))
        .then($rows => $('#task_table tbody').append($rows))
        .catch(e => alert('エラーが発生しました'));
});
