package com.viva.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viva.utils.Hasher;
import com.viva.utils.ShelfLogger;
import com.viva.BookShelfAutomation;
import com.viva.dao.MemberRepository;
import com.viva.entity.Member;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	ShelfLogger logger = new ShelfLogger(BookShelfAutomation.class);

	public List<Member> getAllMembers() {
		List<Member> members = new ArrayList<>();
		memberRepository.findAll().forEach(members::add);
		return members;
	}

	public Optional<Member> getMember(int id) {
		return memberRepository.findById(id);
	}

	public void addMember(Member member) throws NoSuchAlgorithmException {
		try {
			member.setMemberPassword(Hasher.hashingFunction(member.getMemberPassword()));
			member.setWishedBooksAvailable(new ArrayList<Integer>());
			member.setBookCopyIssued(new ArrayList<String>());
			member.setSuspended(false);
			memberRepository.save(member);
		} catch (Exception e) {
			logger.error("Email Already in Use!");
		}
	}

	public void updateMember(Member member, int id) {
		Optional<Member> optionFromDb = getMember(id);

		if (optionFromDb.isPresent()) {
			Member memberFromDb = optionFromDb.get();
			if (member.getFineAccumulated() == 0.0)
				member.setFineAccumulated(memberFromDb.getFineAccumulated());
			if (member.getMemberAddress() == null)
				member.setMemberAddress(memberFromDb.getMemberAddress());
			if (member.getMemberDob() == null)
				member.setMemberDob(memberFromDb.getMemberDob());
			if (member.getMemberEmail() == null)
				member.setMemberEmail(memberFromDb.getMemberEmail());
			if (member.getMemberPassword() == null)
				member.setMemberPassword(memberFromDb.getMemberPassword());
			member.setMemberId(id);
			memberRepository.save(member);
		} else
			logger.error("Member Not Found!");
	}

	public void deleteMember(int id) {
		memberRepository.deleteById(id);
	}

	public Member getMemberByEmail(String email) {
		return memberRepository.getMemberByMemberEmail(email);
	}

	public boolean memberValidation(Member member) throws NoSuchAlgorithmException {
		Member memberFromDb = getMemberByEmail(member.getMemberEmail());
		String hashedPassword = Hasher.hashingFunction(member.getMemberPassword());
		return hashedPassword.equals(memberFromDb.getMemberPassword());
	}

	public Member validatedMember(Member member) throws NoSuchAlgorithmException {
		if (getMemberByEmail(member.getMemberEmail()) == null) {
			logger.error("Email not Registered!");
		} else {
			Member memberFromDb = getMemberByEmail(member.getMemberEmail());
			String hashedPassword = Hasher.hashingFunction(member.getMemberPassword());
			if (hashedPassword.equals(memberFromDb.getMemberPassword()))
				return memberFromDb;
		}
		return null;
	}

	public Member getValidation(Member member) {
		if (getMemberByEmail(member.getMemberEmail()) == null) {
			logger.error("Email ID : " + member.getMemberEmail() + " not registered as Member!");
			return null;
		} else {
			try {
				logger.info("Member validation post request for Member : "
						+ getMemberByEmail(member.getMemberEmail()).getMemberId());
				return validatedMember(member);
			} catch (NoSuchAlgorithmException e) {
				logger.error("Member validation request failed for Member :"
						+ getMemberByEmail(member.getMemberEmail()).getMemberId());
				logger.error(e.getMessage());
				logger.error("Context : ", e);
				return null;
			}
		}
	}
}
