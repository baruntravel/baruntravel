import React, { useCallback, useEffect, useState } from "react";
import Slider from "react-slick";
// import "../kakaoMapPage/node_modules/slick-carousel/slick/slick.css";
// import "../kakaoMapPage/node_modules/slick-carousel/slick/slick-theme.css";
import styles from "./routeDetailPage.module.css";
import DetailHeader from "../../components/common/detailHeader/detailHeader";
import ImageMap from "../../components/routeDetailPage/imageMap/imageMap";
import Avatar from "antd/lib/avatar/avatar";
import { getRouteDetail, onHandleRouteLike } from "../../api/routeAPI";
import InputRootName from "../../components/common/inputRootName/inputRootName";
import {
  onDeleteRouteReview,
  onEditRouteReview,
  onHandleRouteReviewLike,
  onReceiveRouteReview,
  onUploadRouteReview,
} from "../../api/reviewAPI";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import PortalAuth from "../../containers/portalAuth/portalAuth";
import { StarFilled, UserOutlined } from "@ant-design/icons";
import { useHistory } from "react-router-dom";
import Loading from "../../components/common/loading/loading";
import PlaceInRouteDetail from "../../components/routeDetailPage/placeInRouteDetail/placeInRouteDetail";
import ImagesZoom from "../../components/common/reviewComponents/imagesZoom/imagesZoom";
import ReviewList from "../../components/common/reviewComponents/reviewList/reviewList";
import Header from "../../components/common/header/header";
import MoreReviewList from "../../components/common/reviewComponents/moreReviewList/moreReviewList";
import { Drawer } from "antd";

const RouteDetailPage = (props) => {
  const userStates = useRecoilValue(userState);
  const history = useHistory();

  const [routeId, setRouteId] = useState(window.location.pathname.split("/").pop()); // url 마지막 부분이 ID이다.

  const [loading, setLoading] = useState(true);
  const [showImagesZoom, setShowImagesZoom] = useState(false);
  const [imageIndex, setImageIndex] = useState(0);
  const [imagePlaceName, setImagePlaceName] = useState("");
  const [moreReview, setMoreReview] = useState(false);
  const [openInputName, setOpenInputName] = useState(false);
  const [needLogin, setNeedLogin] = useState(false);
  const [routeDetail, setRouteDetail] = useState({});
  const [images, setImages] = useState([]); // 이미지와 이름을 같이 저장
  const [postImages, setPostImages] = useState([]); // 이미지만 저장 (줌을 위한 이미지)
  const [reviewDatas, setReviewDatas] = useState([]); // 리뷰들을 불러와 저장할 state

  // pagination option
  const [paramsResultInfo, setParamsResultInfo] = useState({ totalReviewCount: 0, last: false });
  const [params, setParams] = useState({ page: 0, size: 5, sortType: "latest" });

  // slider option
  const [settings, setSettings] = useState({
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  });

  // 좋아요 테스트
  const [liked, setLiked] = useState(false);
  const onHandleLike = useCallback(() => {
    setLiked(true);
    onHandleRouteLike(routeId);
  }, [routeId]);
  const onHandleUnlike = useCallback(() => {
    setLiked(false);
    onHandleRouteLike(routeId);
  }, [routeId]);

  const onCloseInputName = useCallback(() => {
    setOpenInputName(false);
  }, []);
  const onOpenInputName = useCallback(() => {
    setOpenInputName(true);
  }, []);
  const onZoom = useCallback(() => {
    setShowImagesZoom(true);
  }, []);
  const onClose = useCallback(() => {
    setShowImagesZoom(false);
  }, []);
  const onOpenPortalAuth = useCallback(() => {
    setNeedLogin(true);
  }, []);
  const onClosePortalAuth = useCallback(() => {
    setNeedLogin(false);
  }, []);
  const onOpenMoreReview = useCallback(() => {
    setMoreReview(true);
  }, []);
  const onCloseMoreReview = useCallback(() => {
    setMoreReview(false);
  }, []);

  const onGetReviewList = useCallback(
    async (paramsArg) => {
      if (paramsResultInfo.last && paramsArg.page !== 0) {
        return;
      }
      setParams(paramsArg);
      const reviews = await onReceiveRouteReview(routeId, paramsArg);
      setParamsResultInfo({ totalReviewCount: reviews.totalElements, last: reviews.last });
      if (paramsArg.page > 0) {
        setReviewDatas((prev) => [...prev].concat(reviews.content));
      } else {
        setReviewDatas(reviews.content);
      }
    },
    [paramsResultInfo.last, routeId]
  );

  const onGetReviewWhenScroll = useCallback(() => {
    if (!params.last) {
      const updatedParams = { ...params, page: params.page + 1 };
      onGetReviewList(updatedParams);
    }
  }, [onGetReviewList, params]);

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
      const res = await onUploadRouteReview(routeId, formData); // 추후에 root ID 동적으로 받아오는 걸 구현 후 수정
      if (res) {
        onSortReviewForDate();
      }
    },
    [onSortReviewForDate, routeId]
  );

  const onEditReview = useCallback(async (reviewId, formData, updateReview) => {
    const result = await onEditRouteReview(reviewId, formData);
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
  }, []);

  const onDeleteReview = useCallback(async (reviewId) => {
    const result = await onDeleteRouteReview(reviewId);
    if (result) {
      setReviewDatas((prev) => {
        const updatedDatas = [...prev].filter((review) => review.id !== reviewId);
        return updatedDatas;
      });
    }
  }, []);

  const onLikeReview = useCallback((reviewId) => {
    onHandleRouteReviewLike(reviewId);
  }, []);
  const onUnlikeReview = useCallback((reviewId) => {
    onHandleRouteReviewLike(reviewId);
  }, []);

  const afterSliderChange = useCallback(
    (index) => {
      setImageIndex(index);
      setImagePlaceName(images[index][1]);
    },
    [images]
  );

  useEffect(() => {
    if (!userStates.isLogin) {
      // 로그인 하지않으면 기존 페이지로 이동
      history.push("/");
    }
    async function getRouteDetailInfo() {
      // 루트 상세페이지의 정보를 받아옴
      const route = await getRouteDetail(routeId);
      await setRouteDetail(route);
      const route_images = route && route.places.map((place) => [place.image, place.name]);
      if (route_images) {
        const images = route_images.filter((item) => item[0]); // 이미지가 존재하는것만 뽑아낸다.
        setPostImages(images.map((img) => img[0])); // 이미지만 뽑아서 저장
        setImages(images); // 이미지와 이름을 세트로 저장
        setImagePlaceName(images[0][1]); // 첫 이미지의 장소 이름 저장
      }
      setLoading(false);
    }
    getRouteDetailInfo();
    onGetReviewList(params);
    // setLoading(false);
  }, [history, routeId, userStates]);

  if (!userStates.isLogin) {
    // 로그인 하지않으면 기존 페이지로 이동
    history.push("/");
  }

  if (loading) {
    return (
      <>
        <Loading />
      </>
    );
  }
  if (!routeDetail) {
    return (
      <>
        <div>해당 정보가 없습니다.</div>
      </>
    );
  }
  return (
    <div className={styles.RouteDetailPage}>
      {/* <DetailHeader liked={liked} onHandleLike={onHandleLike} onHandleUnlike={onHandleUnlike} /> */}
      <Header title={`${routeDetail.creator.name}'s 경로`} />
      {images && (
        <div className={styles.sliderContainer} onClick={onZoom}>
          <Slider {...settings} afterChange={(index) => afterSliderChange(index)}>
            {images.map((v, index) => (
              <div key={index} className={styles.imageContainer}>
                <img className={styles.img} src={v[0]} alt="upload" />
              </div>
            ))}
          </Slider>
          <div className={styles.slider__placeNameBox}>
            <span className={styles.slider__placeName}>{imagePlaceName}</span>
          </div>
        </div>
      )}
      <section className={styles.body}>
        <div className={styles.authorBox}>
          <Avatar
            src={(routeDetail.creator && routeDetail.creator.avatar) || ""}
            size="large"
            style={{
              width: "50px",
              height: "50px",
              border: "2px solid white",
            }}
            icon={<UserOutlined />}
          />
          <div className={styles.nicknameBox}>
            <span className={styles.nickname}>{routeDetail.creator && routeDetail.creator.name}</span>
            <span className={styles.dateReview}>{`${routeDetail.createdAt}`}</span>
          </div>
        </div>
        <div className={styles.reviewInfo}>
          <StarFilled style={{ color: "#eb2f96" }} />
          <span className={styles.reviewInfo__score}>{routeDetail.score}</span>
          <span className={styles.reviewInfo__reviewCount}>{`(${routeDetail.reviewCount})`}</span>
        </div>
        <div className={styles.titleBox}>
          <h2 className={styles.title}>{routeDetail.name}</h2>
        </div>
        <div className={styles.placesContainer}>
          <div className={styles.contentBox}>
            <span className={styles.content}>이 장소를 다녀왔어요</span>
          </div>
          {routeDetail && (
            <div className={styles.imageMap}>
              <ImageMap places={routeDetail.places} centerX={routeDetail.centerX} centerY={routeDetail.centerY} />
            </div>
          )}
          <div className={styles.placesBox}>
            {routeDetail.places &&
              routeDetail.places.map((place, index) => (
                <div key={index} className={styles.placeBox}>
                  <PlaceInRouteDetail place={place} />
                </div>
              ))}
          </div>
        </div>
        <div className={styles.buttonBox}>
          <button className={styles.button} onClick={onOpenInputName}>
            일정으로 담기
          </button>
          <span className={styles.wishCount}>{`${routeDetail.reviewCount}명이 좋아해요`}</span>
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
        {routeDetail.reviewCount > 5 && (
          <div className={styles.buttonBox}>
            <button className={styles.moreButton} onClick={onOpenMoreReview}>{`${
              routeDetail.reviewCount - 4
            }개의 리뷰 더보기`}</button>
          </div>
        )}
      </section>
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
      {showImagesZoom && <ImagesZoom images={postImages} onClose={onClose} index={imageIndex} />}
      {openInputName && <InputRootName onClose={onCloseInputName} />}
      {needLogin && <PortalAuth onClose={onClosePortalAuth} />}
    </div>
  );
};

export default RouteDetailPage;
