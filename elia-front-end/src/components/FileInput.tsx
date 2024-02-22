import { FileInputProps } from '../types/prop-types';

export default function FileInput({ onFileAdd }: FileInputProps) {
  return (
    <div className='flex justify-center flex-col items-center'>
      <input
        className='block max-w-sm text-sm p-1 mb-2 text-gray-900 border border-indigo-950  rounded-lg cursor-pointer bg-white focus:outline-none   '
        id='file_input'
        type='file'
        accept='.svg'
        onChange={onFileAdd}
      />
      <button className='py-1 px-4 mt-2 rounded-md bg-blue-700 text-white'>
        Reset To Default Svg
      </button>
    </div>
  );
}
