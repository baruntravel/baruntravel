import React, { useCallback, useState } from "react";
import styles from "./sideMyProfile.module.css";
import { useRecoilState, useResetRecoilState } from "recoil";
import Avatar from "antd/lib/avatar/avatar";
import { useHistory } from "react-router-dom";
import {
  DownloadOutlined,
  EditOutlined,
  ExportOutlined,
  HomeOutlined,
  SettingOutlined,
  SwapOutlined,
  UserOutlined,
} from "@ant-design/icons";
import PortalAuth from "../../../containers/portalAuth/portalAuth";
import { userState } from "../../../recoil/userState";
import LogoutConfirm from "../logoutConfirm/logoutConfirm";
import { Drawer } from "antd";
import ProfileEdit from "../profileEdit/profileEdit";

import { onLogout } from "../../../api/authAPI";

const SideMyProfile = ({ handleClose }) => {
  const [userStates, setUserStates] = useRecoilState(userState);
  const isLogin = userStates.isLogin;
  const history = useHistory();
  const [openAuth, setOpenAuth] = useState(false);
  const [openLogout, setOpenLogout] = useState(false);
  const [openEditProfile, setOpenEditProfile] = useState(false);

  const onOpenAuth = useCallback(() => {
    setOpenAuth(true);
  }, []);
  const onCloseAuth = useCallback(() => {
    setOpenAuth(false);
  }, []);
  const onOpenLogout = useCallback(() => {
    setOpenLogout(true);
  }, []);
  const onCloseLogout = useCallback(() => {
    setOpenLogout(false);
  }, []);
  const onOpenEditProfile = useCallback(() => {
    setOpenEditProfile(true);
  }, []);
  const onCloseEditProfile = useCallback(() => {
    setOpenEditProfile(false);
  }, []);

  const onUpdateUserProfile = useCallback(
    (data) => {
      const { email, name, avatar } = data;
      setUserStates((prev) => {
        const updated = { ...prev, email, name, avatar };
        return updated;
      });
    },
    [setUserStates]
  );

  const goToHome = useCallback(() => {
    history.push("/");
  }, [history]);

  const goToDetailProfile = useCallback(
    (e) => {
      const link = e.target.closest(`.${styles["section__tagBox"]}`).dataset
        .link;
      history.push({ pathname: "/detailProfile", state: { link } });
    },
    [history]
  );

  const resetList = useResetRecoilState(userState);
  const onHandleLogout = useCallback(() => {
    resetList();
    onLogout();
  }, [resetList]);
  return (
    <div className={styles.SideMyProfile}>
      <div className={styles.header}>
        {isLogin ? (
          <div className={styles.avatarBox}>
            <Avatar
              src={userStates.avatar || ""}
              size={64}
              style={{
                border: "2px solid white",
              }}
              icon={<UserOutlined />}
            />
            <div className={styles.nicknameBox}>
              <span>Welcome!</span>
              <span className={styles.nickname}>{userStates.name}</span>
            </div>
          </div>
        ) : (
          <div className={styles.avatarBox} onClick={onOpenAuth}>
            <Avatar
              size={64}
              style={{
                border: "2px solid white",
              }}
              icon={<UserOutlined />}
            />
            <div className={styles.nicknameBox}>
              <span>Welcome!</span>
              <span className={styles.nickname}>로그인이 필요해요</span>
            </div>
          </div>
        )}
      </div>
      {isLogin ? (
        <section className={styles.section}>
          <div className={styles.tags}>
            <div className={styles.section__tagBox} onClick={goToHome}>
              <div className={styles.icon}>
                <HomeOutlined />
              </div>
              <span className={styles.section__tag}>시작하기</span>
            </div>
            <div
              className={styles.section__tagBox}
              onClick={goToDetailProfile}
              data-link="내경로"
            >
              <div className={styles.icon}>
                <DownloadOutlined />
              </div>
              <span className={styles.section__tag}>내 경로</span>
              <span className={styles.tag__count}>3</span>
            </div>
            <div
              className={styles.section__tagBox}
              onClick={goToDetailProfile}
              data-link="공유경로"
            >
              <div className={styles.icon}>
                <SwapOutlined />
              </div>
              <span className={styles.section__tag}>공유 경로</span>
              <span className={styles.tag__count}>3</span>
            </div>
            <div
              className={styles.section__tagBox}
              onClick={goToDetailProfile}
              data-link="리뷰"
            >
              <div className={styles.icon}>
                <EditOutlined />
              </div>
              <span className={styles.section__tag}>리뷰</span>
              <span className={styles.tag__count}>4</span>
            </div>
            <div
              className={styles.section__tagBox}
              onClick={goToDetailProfile}
              data-link="좋아요"
            >
              <div className={styles.icon}>
                <EditOutlined />
              </div>
              <span className={styles.section__tag}>좋아요</span>
              <span className={styles.tag__count}>4</span>
            </div>
            <div className={styles.section__tagBox}>
              <div className={styles.icon} onClick={onOpenEditProfile}>
                <SettingOutlined />
              </div>
              <span className={styles.section__tag} onClick={onOpenEditProfile}>
                Settings
              </span>
            </div>
          </div>
          <div className={styles.section__tagBox}>
            <div className={styles.icon} onClick={onOpenLogout}>
              <ExportOutlined />
            </div>
            <button className={styles.logoutBtn} onClick={onOpenLogout}>
              Logout
            </button>
          </div>
        </section>
      ) : (
        <section className={styles.section}>
          <div className={styles.tags}>
            <div className={styles.section__tagBox} onClick={onOpenAuth}>
              <div className={styles.icon}>
                <UserOutlined />
              </div>
              <span className={styles.section__tag}>로그인</span>
            </div>
          </div>
        </section>
      )}
      {openAuth && <PortalAuth onClose={onCloseAuth} />}
      {openLogout && (
        <LogoutConfirm onLogout={onHandleLogout} onClose={onCloseLogout} />
      )}
      <Drawer
        placement="right"
        closable={false}
        visible={openEditProfile}
        width={"100vw"}
        bodyStyle={{ padding: 0 }}
      >
        <ProfileEdit
          userStates={userStates}
          onClose={onCloseEditProfile}
          onUpdateUserProfile={onUpdateUserProfile}
        />
      </Drawer>
    </div>
  );
};

export default SideMyProfile;
