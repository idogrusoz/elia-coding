import Svg from './Svg';

export default function Canvas() {
  return (
    <div
      style={{
        height: '90vh',
        width: '80vw',
      }}
      className='relative border-indigo-900 border-solid border-2 rounded-m'
    >
      <Svg />
    </div>
  );
}
