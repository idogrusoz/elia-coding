import { useState, useEffect, ChangeEvent } from 'react';
import Canvas from '../components/Canvas';
import Coordinate from '../types/coordinate';
import Shape from '../types/shape';
import axios, { AxiosResponse } from 'axios';
import { GameProps } from '../types/prop-types';
import svg from '../assets/icon.svg?raw';
import FileInput from '../components/FileInput';

export default function Game({ email }: GameProps) {
  const size = 50;
  const [coordinates, setCooordinates] = useState<Coordinate>({
    x: size,
    y: size,
  });
  const [shapeId, setShapeId] = useState<string | null>(null);
  const [showSettings, setShowSettings] = useState(false);
  const [svgFile, setSvgFile] = useState<string>(svg);
  async function updateCoordinates(c: Coordinate) {
    try {
      const data = await executeApiCall('put', `/${shapeId}/move`, c);
      setShapeData(data);
    } catch (error) {
      console.error(error);
    }
  }

  function setShapeData(shape?: Shape) {
    if (shape) {
      setCooordinates(shape.coordinate);
      setShapeId(shape.id);
    }
  }

  function onFileAdd(e: ChangeEvent<HTMLInputElement>) {
    if (e.target.files && e.target.files.length === 1) {
      const reader = new FileReader();
      reader.onload = (event) => {
        const content = event.target?.result;
        if (typeof content === 'string') {
          setSvgFile(content);
        } else if (content instanceof ArrayBuffer) {
          var decoder = new TextDecoder();
          setSvgFile(decoder.decode(content));
        }
      };
      reader.readAsText(e.target.files[0]);
      setShowSettings(false);
    }
  }

  function resetSvgToDefault() {
    setSvgFile(svg)
  }

  async function executeApiCall(
    method: 'post' | 'put' | 'get',
    url: string,
    data?: Coordinate
  ) {
    const response = await axios<Coordinate, AxiosResponse<Shape>>({
      baseURL: 'http://localhost:8080/api/v1/shapes',
      url,
      method,
      data: data ?? coordinates,
      headers: { email },
    });
    return response.data;
  }

  useEffect(() => {
    async function getOrInitShape() {
      let data: Shape | undefined;
      try {
        if (email) {
          data = await executeApiCall('get', ``);
          setShapeData(data);
        }
      } catch (error) {
        if (!data) {
          data = await executeApiCall('post', '');
          setShapeData(data);
        }
      }
    }

    getOrInitShape();
  }, []);

  return (
    <div className='h-screen w-screen overflow-hidden flex justify-center flex-col'>
      <div className='absolute top-3 right-4 bg-slate-300 hover:bg-slate-700 px-3 py2 rounded-md text-black hover:text-white'>
        <button onClick={() => setShowSettings(!showSettings)}>
          {showSettings ? 'Back' : 'Edit'}
        </button>
      </div>
      {showSettings ? (
        <FileInput
          resetSvg={resetSvgToDefault}
          onFileAdd={onFileAdd}
        />
      ) : (
        <div className='h-100 w-100 overflow-hidden flex justify-center items-center'>
          <Canvas
            coordinates={coordinates}
            size={size}
            svg={svgFile}
            updateCoordinates={updateCoordinates}
          />
        </div>
      )}
    </div>
  );
}
