import styles from "./routeCarousel.module.css";
import Slider from "react-slick";
import FavoriteBorderIcon from "@material-ui/icons/FavoriteBorder"; // empty heart icon
import FavoriteIcon from "@material-ui/icons/Favorite"; // filled heart icon
import { Link } from "react-router-dom";

const RouteCarousel = ({ routeItems, handleChange }) => {
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <div className={styles.container}>
      <Slider afterChange={(e) => handleChange(e)} {...settings}>
        {Object.keys(routeItems).map((value, index) => {
          return (
            <Link to={`route/${value}`} target="_blank" key={index}>
              <div className={styles.routeCard}>
                <div className={styles.top}>
                  <div className={styles.routeName}>{routeItems[value].routeName}</div>
                  <div className={styles.creator}>{routeItems[value].creator}</div>
                  <button className={styles.heartButton}>
                    <FavoriteBorderIcon />
                  </button>
                </div>
                <div className={styles.bottom}>
                  {routeItems[value].places.map((value, index) => {
                    return (
                      <div className={styles.places} key={index}>
                        {value.placeName}
                      </div>
                    );
                  })}
                </div>
              </div>
            </Link>
          );
        })}
      </Slider>
    </div>
  );
};

export default RouteCarousel;
