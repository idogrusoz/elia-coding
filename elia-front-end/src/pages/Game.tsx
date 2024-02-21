import { useState, useEffect } from 'react';
import Canvas from '../components/Canvas';
import Coordinate from '../types/coordinate';
import Shape from '../types/shape';
import axios, { AxiosResponse } from 'axios';

export default function Game() {
  const size = 50;
  const [coordinates, setCooordinates] = useState<Coordinate>({
    x: size,
    y: size,
  });
  const [shapeId, setShapeId] = useState<string | null>(null);
  async function updateCoordinates(c: Coordinate) {
    const data = await executeApiCall('put', `/${shapeId}/move`, c);
    setShapeData(data);
  }

  function setShapeData(shape?: Shape) {
    if (shape) {
      setCooordinates(shape.coordinate);
      setShapeId(shape.id);
    }
  }

  async function executeApiCall(
    method: 'post' | 'put' | 'get',
    url: string,
    data?: Coordinate
  ) {
    try {
      const response = await axios<Coordinate, AxiosResponse<Shape>>({
        baseURL: 'http://localhost:8080/api/v1/shapes',
        url,
        method,
        data: data ?? coordinates,
      });
      return response.data;
    } catch (error) {
      console.error(error);
    }
  }

  useEffect(() => {
    async function getOrInitShape() {
      let data: Shape | undefined;
      if (shapeId !== null) {
        console.log(shapeId);
        data = await executeApiCall('get', `/${shapeId}`);
        setShapeData(data);
      }
      if (!data) {
        data = await executeApiCall('post', '');
        setShapeData(data);
      }
    }

    getOrInitShape();
  }, []);

  return (
    <div className='h-screen w-screen overflow-hidden flex justify-center items-center'>
      <Canvas
        coordinates={coordinates}
        size={size}
        updateCoordinates={updateCoordinates}
      />
    </div>
  );
}
