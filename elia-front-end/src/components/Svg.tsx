import { SvgProps } from '../types/prop-types';

export default function Svg({ size, coordinates, onDragEnd, svg }: SvgProps) {
  return (
    <img
      draggable
      onDragEnd={onDragEnd}
      style={{
        position: 'absolute',
        top: coordinates.y - size / 2,
        left: coordinates.x - size / 2,
        width: size,
      }}
      src={`data:image/svg+xml;base64,${btoa(svg)}`}
      width={50}
    />
  );
}
