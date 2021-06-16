import React, { useCallback, useEffect, useRef, useState } from "react";
import styles from "./placeDetailPage.module.css";
import Slider from "react-slick";
import { StarFilled } from "@ant-design/icons";
import { Drawer } from "antd";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import PortalAuth from "../../containers/portalAuth/portalAuth";
import { onReceivePlace } from "../../api/placeAPI";
import {
  onDeleteImageInPlaceReview,
  onDeletePlaceReview,
  onEditPlaceReview,
  onHandlePlaceReviewLike,
  onReceivePlaceReview,
  onUploadPlaceReview,
} from "../../api/reviewAPI";
import { useHistory } from "react-router-dom";
import Loading from "../../components/common/loading/loading";
import DetailHeader from "../../components/common/detailHeader/detailHeader";
import ImagesZoom from "../../components/common/reviewComponents/imagesZoom/imagesZoom";
import MoreReviewList from "../../components/common/reviewComponents/moreReviewList/moreReviewList";
import ReviewList from "../../components/common/reviewComponents/reviewList/reviewList";
import Header from "../../components/common/header/header";

const { kakao } = window;

const PlaceDetailPage = (props) => {
  const userStates = useRecoilValue(userState);
  const history = useHistory();
  const mapRef = useRef();

  const [placeId, setPlaceId] = useState(window.location.pathname.split("/").pop()); // url 마지막 부분이 ID이다.

  const [loading, setLoading] = useState(true); // 처음에는 로딩이 필요하다
  const [needLogin, setNeedLogin] = useState(false);
  const [showImagesZoom, setShowImagesZoom] = useState(false);
  const [imageIndex, setImageIndex] = useState(0);
  const [liked, setLiked] = useState(false);
  const [images, setImages] = useState([]); // 보여줄 메인 이미지 저장소
  const [placeDetail, setPlaceDetail] = useState({});
  const [reviewDatas, setReviewDatas] = useState([]); // 리뷰들을 불러와 저장할 state
  const [moreReview, setMoreReview] = useState(false);
  const [paramsResultInfo, setParamsResultInfo] = useState({ totalReviewCount: 0, last: false });
  const [params, setParams] = useState({ page: 0, size: 5, sortType: "latest" });
  const [settings, setSettings] = useState({
    // slider option
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  });
  // 해당 place의 리뷰를 받아오는 함수
  const onGetReviewList = useCallback(
    async (paramsArg) => {
      if (paramsResultInfo.last) {
        return;
      }
      setParams(paramsArg);
      const reviews = await onReceivePlaceReview(placeId, paramsArg);
      setParamsResultInfo({ totalReviewCount: reviews.totalElements, last: reviews.last });
      if (paramsArg.page > 0) {
        setReviewDatas((prev) => [...prev].concat(reviews.content));
      } else {
        setReviewDatas(reviews.content);
      }
    },
    [placeId]
  );

  const onGetReviewWhenScroll = useCallback(() => {
    if (!params.last) {
      const updatedParams = { ...params, page: params.page + 1 };
      onGetReviewList(updatedParams);
    }
  }, [onGetReviewList, params]);

  const onZoom = useCallback(() => {
    setShowImagesZoom(true);
  }, []);
  const onCloseZoom = useCallback(() => {
    setShowImagesZoom(false);
  }, []);

  const onClosePortalAuth = useCallback(() => {
    setNeedLogin(false);
  }, []);
  const onOpenPortalAuth = useCallback(() => {
    setNeedLogin(true);
  }, []);

  const onOpenMoreReview = useCallback(() => {
    setMoreReview(true);
  }, []);
  const onCloseMoreReview = useCallback(() => {
    setMoreReview(false);
  }, []);

  const onClickLike = useCallback(() => {
    if (userStates.isLogin) {
      console.log("좋아요 API 호출");
      setLiked(true);
    } else {
      setNeedLogin(true);
    }
  }, [userStates]);

  const onClickUnlike = useCallback(() => {
    console.log("좋아요 취소 API 호출");
    setLiked(false);
  }, []);

  const onSortReviewForDate = useCallback(() => {
    const updatedParams = { ...params, page: 0, sortType: "latest" };
    onGetReviewList(updatedParams);
  }, [onGetReviewList, params]);

  const onSortReviewForLike = useCallback(() => {
    const updatedParams = { ...params, page: 0, sortType: "best" };
    onGetReviewList(updatedParams);
  }, [onGetReviewList, params]);

  const onUploadReview = useCallback(
    async (formData) => {
      await onUploadPlaceReview(placeId, formData);
      onSortReviewForDate();
    },
    [onSortReviewForDate, placeId]
  );

  const onDeleteReview = useCallback(
    async (reviewId) => {
      const result = await onDeletePlaceReview(placeId, reviewId);
      if (result) {
        setReviewDatas((prev) => {
          const updatedDatas = [...prev].filter((review) => review.id !== reviewId);
          return updatedDatas;
        });
      }
    },
    [placeId]
  );

  const onEditReview = useCallback(
    async (reviewId, formData, updateReview) => {
      const result = await onEditPlaceReview(placeId, reviewId, formData);
      if (result) {
        setReviewDatas((prev) => {
          const updated = [...prev].map((review) => {
            if (review.id !== reviewId) {
              return review;
            } else {
              return updateReview;
            }
          });
          return updated;
        });
      }
    },
    [placeId]
  );

  const onLikeReview = useCallback(
    (reviewId) => {
      onHandlePlaceReviewLike(placeId, reviewId);
    },
    [placeId]
  );
  const onUnlikeReview = useCallback(
    (reviewId) => {
      onHandlePlaceReviewLike(placeId, reviewId);
    },
    [placeId]
  );

  const onDeleteReviewImage = useCallback(
    // api 수정 후 적용해야될 함수
    async (reviewId, reviewImageId) => {
      await onDeleteImageInPlaceReview(placeId, reviewId, reviewImageId);
    },
    [placeId]
  );

  const afterSliderChange = useCallback((index) => {
    setImageIndex(index);
  }, []);

  useEffect(() => {
    async function getPlaceDetail() {
      // place 디테일 정보를 불러오는 함수
      const placeDetailInfo = await onReceivePlace(placeId);
      if (placeDetailInfo) {
        const images = placeDetailInfo.images.map((image) => image.url);
        setPlaceDetail(placeDetailInfo);
        setImages(images);
        setLoading(false); // loading false를 지도를 그려주기 전에 해줘야한다 -> 렌더링 되지 않은 맵 컨테이너를 참조하기 때문에
        makeMapImage(placeDetailInfo.x, placeDetailInfo.y);
      }
    }
    // map image 만드는 함수
    function makeMapImage(x, y) {
      let markerPosition = new kakao.maps.LatLng(y, x);
      let marker = {
        position: markerPosition,
      };
      let staticMapContainer = mapRef.current,
        staticMapOption = {
          center: new kakao.maps.LatLng(y, x),
          level: 4,
          marker: marker,
        };
      new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
    }
    // placeDetail 받아오는 곳
    getPlaceDetail();
    onGetReviewList(params);
    window.scrollTo(0, 0);
  }, [placeId]);

  useEffect(() => {}, [placeDetail]);

  if (!userStates.isLogin) {
    // 로그인 하지 않았을 때 접근 불가 -> 추후에 API 수정 후 고쳐야됨
    history.push("/");
  }
  if (loading) {
    return (
      <>
        <Loading />
      </>
    );
  }
  if (!placeDetail) {
    return <div>place 정보가 없습니다.</div>;
  }
  return (
    <div className={styles.PlaceDetailPage}>
      {/* <DetailHeader
        liked={liked}
        needLogin={needLogin}
        onOpenPortalAuth={onOpenPortalAuth}
        onClickLike={onClickLike}
        onClickUnlike={onClickUnlike}
      /> */}
      <Header title={placeDetail.name} />
      {images && (
        <div className={styles.slideContainer} onClick={onZoom}>
          <Slider {...settings} afterChange={(index) => afterSliderChange(index)}>
            {images.map((imgSrc, index) => (
              <div key={index} className={styles.imageContainer}>
                <img className={styles.image} src={imgSrc} alt="placeImage" />
              </div>
            ))}
          </Slider>
          <div className={styles.imageCounter}>
            <span>
              {imageIndex + 1} / {images.length}
            </span>
          </div>
        </div>
      )}
      <div className={styles.body}>
        <div className={styles.body__header}>
          <h1 className={styles.body__placeName}>{placeDetail.name}</h1>
          <div className={styles.body__placeInfo}>
            <div className={styles.body__rate}>
              <StarFilled style={{ color: "#eb2f96" }} />
              <span className={styles.body__score}>{parseInt(placeDetail.score)}</span>
              <span className={styles.body__reviewCount}>{`(${paramsResultInfo.totalReviewCount})`}</span>
            </div>
            <span className={styles.body__address}>{placeDetail.address}</span>
          </div>
          <span className={styles.body__openHour}>영업시간 : {placeDetail.openHour}</span>
        </div>
        <div className={styles.body__placeLocation}>
          <div className={styles.body__titleContainer}>
            <h2 className={styles.body__locationTitle}>위치</h2>
          </div>
          <div className={styles.body__addressContainer}>
            <span className={styles.body__placeAddress}>{placeDetail.address}</span>
          </div>
          <div className={styles.body__mapContainer}>
            <div ref={mapRef} className={styles.body__map} />
          </div>
        </div>
        <div className={styles.buttonBox}>
          <button className={styles.button}>장바구니 카트에 담기</button>
          <span className={styles.wishCount}>{`${placeDetail.likes}명이 좋아해요`}</span>
        </div>
        <div className={styles.reviewList}>
          <ReviewList
            userStates={userStates}
            totalReviewCount={paramsResultInfo.totalReviewCount}
            reviewDatas={reviewDatas}
            onOpenPortalAuth={onOpenPortalAuth}
            onUploadReview={onUploadReview}
            onLikeReview={onLikeReview}
            onUnlikeReview={onUnlikeReview}
            onEditReview={onEditReview}
            onDeleteReview={onDeleteReview}
            onSortReviewForDate={onSortReviewForDate}
            onSortReviewForLike={onSortReviewForLike}
          />
        </div>
        <div className={styles.buttonBox}>
          <button className={styles.button} onClick={onOpenMoreReview}>{`${4}개의 리뷰 더보기`}</button>
        </div>
      </div>
      <Drawer // 리뷰 더보기
        visible={moreReview}
        placement="right"
        width="100vw"
        bodyStyle={{ padding: 0 }}
        closeIcon={false}
        style={{
          overflowY: "hidden",
        }}
      >
        <MoreReviewList
          userStates={userStates}
          onCloseMoreReview={onCloseMoreReview}
          reviewDatas={reviewDatas}
          totalReviewCount={paramsResultInfo.totalReviewCount}
          onOpenPortalAuth={onOpenPortalAuth}
          onUploadReview={onUploadReview}
          onLikeReview={onLikeReview}
          onUnlikeReview={onUnlikeReview}
          onEditReview={onEditReview}
          onDeleteReview={onDeleteReview}
          onSortReviewForDate={onSortReviewForDate}
          onSortReviewForLike={onSortReviewForLike}
          onGetReviewWhenScroll={onGetReviewWhenScroll}
        />
      </Drawer>
      {showImagesZoom && <ImagesZoom images={images} onClose={onCloseZoom} index={imageIndex} />}
      {needLogin && <PortalAuth onClose={onClosePortalAuth} />}
    </div>
  );
};

export default PlaceDetailPage;
