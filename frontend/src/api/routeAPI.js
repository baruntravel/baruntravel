import axios from "axios";

export const getMyRoute = async () => {
  return await axios
    .get(`/routes/my`)
    .then((res) => res.data.routes)
    .catch((error) => {
      console.error(error);
      throw new Error(`${error}`);
    });
};

export const makeRoute = async (name, placesData) => {
  const places = placesData.map((place, index) => ({
    id: place.id,
    order: index,
  }));
  const data = {
    name,
    places,
  };
  console.log(places);
  return await axios
    .post(`/route`, data)
    .then((res) => res.data.id || true)
    .catch((error) => {
      console.error(`${error}`);
      return false;
    });
};
