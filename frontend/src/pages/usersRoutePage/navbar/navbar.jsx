import styles from "./navbar.module.css";
import { ShoppingCartOutlined } from "@ant-design/icons";

const Navbar = () => {
  return (
    <div className={styles.container}>
      <div className={styles.backButton}></div>
      <div className={styles.cartButton}>
        <ShoppingCartOutlined style={{ fontSize: "36px" }} className={styles.cartIcon} />
      </div>
    </div>
  );
};

export default Navbar;
