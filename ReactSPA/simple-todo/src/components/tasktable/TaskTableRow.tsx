import Task, { TaskId } from "../../types/Task"

type Props = {
  task: Task;
  onComplete: (task: Task) => void;
  onDelete: (id: TaskId) => void;
};

function TaskTableRow({ task, onComplete, onDelete }: Props) {
  const buttons = <>
    <button type='button' onClick={() => onComplete(task)}>完了</button>
    <button type='button' onClick={() => onDelete(task.id)}>削除</button>
  </>

  return <tr>
    <td>{task.id}</td>
    <td>{task.description}</td>
    <td>{task.isCompleted ? '完了済み' : buttons}</td>
  </tr>
}

export default TaskTableRow;