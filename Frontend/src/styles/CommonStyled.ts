import styled, { css } from "styled-components";
import { ToastContainer } from "react-toastify";
import palette from "@/lib/palette";

export const CommonComponent = css`
  border: 1px solid ${palette.deactivatedGray};
  background-color: ${palette.brandWhite};
`;

export const ColumnContainer = styled.div`
  background-color: #f9fafc;
  padding: 0.5rem 0;
`;

export const ColumnGrid = styled.div<{ column: string }>`
  display: grid;
  grid-template-columns: ${(props) => props.column};
`;

export const ColumnTitleDiv = styled.div`
  text-align: center;
  font-size: 0.75rem;
  font-family: "NanumSquareAcb";
  color: ${palette.brandDarkGray};
`;

export const StyledContainer = styled(ToastContainer)`
  margin-top: 4rem;
  width: 25rem;

  .Toastify__toast-body {
    background: ${palette.brandWhite} !important;
    color: ${palette.brandBlack};
  }

  .Toastify__progress-bar {
    background: ${palette.brandColor} !important;
  }
`;

export const ListItemContainer = styled.div`
  font-size: 0.9rem;
  padding: 0.7rem 0;
  border-bottom: 1px solid ${palette.borderGray};
  /* display: flex; */
  align-items: center;
  justify-content: center;
  text-align: center;
`;
