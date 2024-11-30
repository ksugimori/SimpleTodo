import Task, { TaskId } from "../../types/Task"
import styled from 'styled-components';

type Props = {
  task: Task;
  onComplete: (task: Task) => void;
  onDelete: (id: TaskId) => void;
};

function TaskTableRow({ task, onComplete, onDelete }: Props) {
  const Tr = styled.tr`
    &:hover {
      background-color: #f1f1f1;
    }
  `;

  const Td = styled.td`
    &:has(> button) {
      padding: 0 1rem;
    }
  `;

  const buttons = (<>
    <button type='button' onClick={() => onComplete(task)}>完了</button>
    <button type='button' onClick={() => onDelete(task.id)}>削除</button>
  </>);

  return (<Tr>
    <Td>{task.id}</Td>
    <Td>{task.description}</Td>
    <Td>{task.isCompleted ? '完了済み' : buttons}</Td>
  </Tr>);
}

export default TaskTableRow;