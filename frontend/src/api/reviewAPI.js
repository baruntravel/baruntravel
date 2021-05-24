import axios from "axios";

// 경로 리뷰 API

// 경로 생성
export const onUploadRouteReview = (routeId, formData) => {
  return axios
    .post(`/route/${routeId}/review`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 경로 상세 정보 받아오기
export const onReceiveRouteReview = async (routeId) => {
  const result = await axios
    .get(`/route/${routeId}/reviews`)
    .then((res) => res.data.reviews)
    .catch((error) => {
      console.log(error);
      throw new Error(`unExpected Error ${error}`);
    });
  return result;
};

// 경로 리뷰 수정하기
export const onEditRouteReview = async (routeId, formData) => {
  return await axios
    .post(`/route/review/${routeId}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 경로 리뷰 삭제하기
export const onDeleteRouteReview = async (routeId) => {
  await axios
    .delete(`/route/review/${routeId}`)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unHandled error ${error}`);
    });
};

export const onHandleRouteReviewLike = (reviewId) => {
  axios
    .post(`/route/review/${reviewId}/like`)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 장소 리뷰 API
export const onReceivePlaceReview = (placeId) => {
  // 장소 리뷰 받아오기
  return axios
    .get(`/place/${placeId}/review`)
    .then((res) => {
      return res.data.reviews;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
};

export const onUploadPlaceReview = (placeId, formData) => {
  return axios
    .post(`/place/${placeId}/review`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// export const onEditPlaceReview = (placeId, routeId, formData) => {
//   return axios
// }
