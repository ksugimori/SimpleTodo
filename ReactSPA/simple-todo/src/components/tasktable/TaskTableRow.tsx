import Task, { TaskId } from "../../types/Task"
import styled from 'styled-components';

type Props = {
  task: Task;
  onComplete: (task: Task) => void;
  onDelete: (id: TaskId) => void;
};

function TaskTableRow({ task, onComplete, onDelete }: Props) {
  const HoverableTr = styled.tr`
    &:hover {
      background-color: #f1f1f1;
    }
  `;

  const buttons = <>
    <button type='button' onClick={() => onComplete(task)}>完了</button>
    <button type='button' onClick={() => onDelete(task.id)}>削除</button>
  </>

  return <HoverableTr>
    <td>{task.id}</td>
    <td>{task.description}</td>
    <td>{task.isCompleted ? '完了済み' : buttons}</td>
  </HoverableTr>
}

export default TaskTableRow;