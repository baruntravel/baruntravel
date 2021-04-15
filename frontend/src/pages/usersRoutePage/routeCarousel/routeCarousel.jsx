import styles from "./routeCarousel.module.css";
import { Carousel } from "react-responsive-carousel";
import FavoriteBorderIcon from "@material-ui/icons/FavoriteBorder"; // empty heart icon
import FavoriteIcon from "@material-ui/icons/Favorite"; // filled heart icon
import { Link } from "react-router-dom";
import RouteDetailPage from "../routeDetailPage/routeDetailPage";

const RouteCarousel = ({ routeItems, handleChange }) => {
  return (
    <div className={styles.container}>
      <Carousel
        infiniteLoop
        renderIndicator={false}
        autoPlay={false}
        showStatus={false}
        onChange={(e) => handleChange(e)}
      >
        {Object.keys(routeItems).map((value, index) => {
          return (
            <Link to={`routes/${value}`} target="_blank" key={index}>
              <div className={styles.routeCard}>
                <div className={styles.top}>
                  <div className={styles.routeName}>
                    {routeItems[value].routeName}
                  </div>
                  <div className={styles.creator}>
                    {routeItems[value].creator}
                  </div>
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
      </Carousel>
    </div>
  );
};

export default RouteCarousel;
