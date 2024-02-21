import { useState, useMemo } from 'react';
import Game from './pages/Game';
import Registration from './pages/Registration';

export default function App() {
  const [email, setEmail] = useState<string>('');

  function getUserInput(input: string) {
    if (input === null) {
      return;
    } else if (input.trim().length > 0) {
      setEmail(input.trim());
    }
  }

  const isValidEmail = useMemo(
    () => email !== null && email.length > 0,
    [email]
  );

  return isValidEmail ? (
    <Game email={email} />
  ) : (
    <Registration getUserInput={getUserInput} />
  );
}
