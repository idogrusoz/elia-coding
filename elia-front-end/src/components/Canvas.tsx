import { RefObject, useRef } from 'react';
import Svg from './Svg';
import { CanvasProps } from '../types/prop-types';

export default function Canvas({ coordinates, size, svg, updateCoordinates }: CanvasProps) {
  const canvas = useRef<HTMLDivElement>(null);

  async function onDragEnd(e: DragEvent) {
    if (canvas.current == null) {
      return;
    }
    if (isDroppedInTheCanvas(canvas, e)) {
      const wrapperTop = canvas.current?.offsetTop ?? 0;
      const wrapperLeft = canvas.current?.offsetLeft ?? 0;
      updateCoordinates({
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
        svg={svg}
        coordinates={coordinates}
      />
    </div>
  );
}
