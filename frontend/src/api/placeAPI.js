import axios from "axios";

export const onReceivePlace = (placeId) => {
  return axios
    .get(`/place/${placeId}`)
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
};
