import Coordinate from './coordinate';

export type SvgProps = {
  coordinates: Coordinate;
  onDragEnd: any;
  size: number;
};

export type CanvasProps = {
  coordinates: Coordinate;
  size: number;
  updateCoordinates: (c:Coordinate) => void;
};

export type RegistrationProps = {
  getUserInput: (input: string) => void;
};

export type GameProps = {
  email: string;
};

