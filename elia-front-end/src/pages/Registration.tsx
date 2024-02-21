import { FormEvent, useState } from 'react';
import { RegistrationProps } from '../types/prop-types';

export default function Registration({ getUserInput }: RegistrationProps) {
  const [input, setInput] = useState('');
  function handleSubmit(e: FormEvent) {
    e.preventDefault();
    getUserInput(input);
  }

  return (
    <div className='flex flex-col justify-center items-center h-screen'>
      <form
        onSubmit={(e) => handleSubmit(e)}
        action='submit'
        className='flex flex-col justify-center items-center'
      >
        <input
          type='text'
          className='border-b-indigo-950 border-b-2 p-2 m-2 min-w-64'
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder='Enter email...'
        />
        <button
          className='py-2 px-4 rounded-md bg-blue-700 text-white'
          type='submit'
        >
          Submit
        </button>
      </form>
    </div>
  );
}
