import { ChangeEvent } from 'react';
import Coordinate from './coordinate';

export type SvgProps = {
  coordinates: Coordinate;
  onDragEnd: any;
  size: number;
  svg: string;
};

export type CanvasProps = {
  coordinates: Coordinate;
  size: number;
  svg: string;
  updateCoordinates: (c:Coordinate) => void;
};

export type RegistrationProps = {
  getUserInput: (input: string) => void;
};

export type GameProps = {
  email: string;
};

export type FileInputProps = {
  onFileAdd: (e: ChangeEvent<HTMLInputElement>) => void
  resetSvg: () => void
}

