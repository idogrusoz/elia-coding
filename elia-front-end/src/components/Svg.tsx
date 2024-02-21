import svg from '../assets/icon.svg';
import { SvgProps } from '../types/prop-types';

export default function Svg({ size, coordinates, onDragEnd }: SvgProps) {
  return (
    <img
      draggable
      onDragEnd={onDragEnd}
      style={{
        position: 'absolute',
        top: coordinates.y - size / 2,
        left: coordinates.x - size / 2,
      }}
      src={svg}
      width={size}
    />
  );
}
