import axios from "axios";

// 경로 리뷰 API

// 경로 리뷰 생성
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

// 경로 리뷰 받아오기
export const onReceiveRouteReview = (routeId, params) => {
  return axios
    .get(`/route/${routeId}/reviews`, {
      params,
    })
    .then((res) => res.data)
    .catch((error) => {
      console.log(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 경로 리뷰 수정하기
export const onEditRouteReview = (routeId, formData) => {
  return axios
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
export const onDeleteRouteReview = (routeId) => {
  axios
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

// 장소 리뷰 받아오기
export const onReceivePlaceReview = (placeId, params) => {
  return axios
    .get(`/place/${placeId}/review`, {
      params,
    })
    .then((res) => {
      return res.data;
    })
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 장소 리뷰 작성
export const onUploadPlaceReview = (placeId, formData) => {
  return axios
    .post(`/place/${placeId}/review`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => res.id)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 장소 리뷰 수정
export const onEditPlaceReview = (placeId, reviewId, formData) => {
  return axios
    .post(`/place/${placeId}/review/${reviewId}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 장소 리뷰 삭제하기
export const onDeletePlaceReview = (placeId, reviewId) => {
  return axios
    .delete(`/place/${placeId}/review/${reviewId}`)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unHandled error ${error}`);
    });
};

// 장소 리뷰 좋아요
export const onHandlePlaceReviewLike = (placeId, reviewId) => {
  return axios
    .post(`/place/${placeId}/review/${reviewId}/like`)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

// 장소 리뷰 수정 시 이미지 삭제
export const onDeleteImageInPlaceReview = (placeId, reviewId, reviewImageId) => {
  return axios
    .delete(`/place/${placeId}/review/${reviewId}/image/${reviewImageId}`)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};
