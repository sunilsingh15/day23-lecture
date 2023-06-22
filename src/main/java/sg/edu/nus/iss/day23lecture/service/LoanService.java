package sg.edu.nus.iss.day23lecture.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day23lecture.model.Customer;
import sg.edu.nus.iss.day23lecture.model.Loan;
import sg.edu.nus.iss.day23lecture.model.LoanDetails;
import sg.edu.nus.iss.day23lecture.model.Video;
import sg.edu.nus.iss.day23lecture.repo.CustomerRepo;
import sg.edu.nus.iss.day23lecture.repo.LoanDetailsRepo;
import sg.edu.nus.iss.day23lecture.repo.LoanRepo;
import sg.edu.nus.iss.day23lecture.repo.VideoRepo;

@Service
public class LoanService {

    @Autowired
    LoanRepo lRepo;

    @Autowired
    LoanDetailsRepo ldRepo;

    @Autowired
    CustomerRepo cRepo;

    @Autowired
    VideoRepo vRepo;

    public Boolean loanVideo(Customer customer, List<Video> videos) {
        Boolean bLoanSuccessful = false;

        // pre-requisites
        // 1. retrieve all video records
        List<Video> allVideos = vRepo.findAll();

        // check that all videos are available, count of videos must be > 0
        Boolean bAvailable = true;
        for (Video video : videos) {
            List<Video> filteredVideoList = allVideos.stream()
                    .filter(v -> v.getId().equals(video.getId()))
                    .collect(Collectors.toList());

            if (!filteredVideoList.isEmpty()) {
                if (filteredVideoList.get(0).getAvailable_count() == 0) {
                    bAvailable = false;
                    // throw a custom exception / built in exception
                } else {
                    // reducing video quantity in video table by 1
                    // for the video that the user loaned
                    Video updateVideoEntity = filteredVideoList.get(0);
                    updateVideoEntity.setAvailable_count(updateVideoEntity.getAvailable_count() - 1);
                    vRepo.updateVideo(updateVideoEntity);
                }
            }
        }

        // 2. create a loan record

        // 3. create the loan details that tie to the loan
        if (bAvailable) {
            Loan loan = new Loan();
            loan.setCustomerID(customer.getId());
            loan.setLoanDate(Date.valueOf(LocalDate.now()));

            Integer createdLoanID = lRepo.createLoan(loan);

            for (Video video : videos) {
                LoanDetails loanDetails = new LoanDetails();
                loanDetails.setLoanID(createdLoanID);
                loanDetails.setVideoID(video.getId());

                ldRepo.createLoanDetails(loanDetails);
            }

            bLoanSuccessful = true;
        }

        return bLoanSuccessful;
    }

}
