import { RefObject, useRef, useState } from 'react';
import Svg from './Svg';
import Coordinate from '../types/coordinate';

export default function Canvas() {
  const size = 50;
  const canvas = useRef<HTMLDivElement>(null);
  const [coordinates, setCooordinates] = useState<Coordinate>({
    x: size,
    y: size,
  });

  async function onDragEnd(e: DragEvent) {
    if (canvas.current == null) {
      return;
    }
    if (isDroppedInTheCanvas(canvas, e)) {
      const wrapperTop = canvas.current?.offsetTop ?? 0;
      const wrapperLeft = canvas.current?.offsetLeft ?? 0;
      setCooordinates({
        x: e.pageX - wrapperLeft,
        y: e.pageY - wrapperTop,
      });
    }
  }

  function isDroppedInTheCanvas(
    canvas: RefObject<HTMLDivElement>,
    e: DragEvent
  ) {
    const canvasRect = canvas.current?.getBoundingClientRect();
    return (
      canvasRect &&
      e.clientX >= canvasRect.left &&
      e.clientX <= canvasRect.right &&
      e.clientY >= canvasRect.top &&
      e.clientY <= canvasRect.bottom
    );
  }
  return (
    <div
      ref={canvas}
      style={{
        height: '90vh',
        width: '80vw',
      }}
      className='relative border-indigo-900 border-solid border-2 rounded-m'
    >
      <Svg
        onDragEnd={onDragEnd}
        size={size}
        coordinates={coordinates}
      />
    </div>
  );
}
