import Task, { TaskId } from "../../types/Task"

type Props = {
  task: Task;
  onComplete: (id: TaskId) => void;
  onDelete: (id: TaskId) => void;
};

function TaskTableRow({ task, onComplete, onDelete }: Props) {
  const buttons = <>
    <button type='button' onClick={() => onComplete(task.id)}>完了</button>
    <button type='button' onClick={() => onDelete(task.id)}>削除</button>
  </>

  return <tr>
    <td>{task.id}</td>
    <td>{task.subject}</td>
    <td>{task.isCompleted ? '完了済み' : buttons}</td>
  </tr>
}

export default TaskTableRow;