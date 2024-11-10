import { FormEventHandler } from "react";

type Props = {
  onSubmit: (subject: string) => void;
}

function TaskInputForm({ onSubmit }: Props) {
  const handler: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    const form: HTMLFormElement = event.currentTarget;

    const subject = new FormData(form).get("subject") as string
    onSubmit(subject);

    form.reset();
  }

  return (<form onSubmit={handler}>
    <input type="text" name="subject" required />
    <button type="submit">登録</button>
  </form>)
}

export default TaskInputForm;