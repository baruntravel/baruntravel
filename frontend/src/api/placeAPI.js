import axios from "axios";

export const getPlaceDetail = (placeId) => {
  return axios
    .get(`/place/${placeId}`)
    .then((res) => res.data)
    .catch((error) => {
      console.error(error);
      return false;
    });
};
