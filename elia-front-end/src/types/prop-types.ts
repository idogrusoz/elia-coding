import Coordinate from './coordinate';

export type SvgProps = {
  coordinates: Coordinate;
  onDragEnd: any;
  size: number;
};

export type CanvasProps = {
  coordinates: Coordinate;
  size: number;
};
