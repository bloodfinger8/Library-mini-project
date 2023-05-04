package com.group.libraryapp.service.user

import com.group.libraryapp.*
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.command.SignUpCommand
import com.group.libraryapp.dto.user.response.SignUpResponse
import com.group.libraryapp.exception.EmailAlreadyExistsException
import com.group.libraryapp.usecase.user.SignUpUseCase
import com.group.libraryapp.usecase.user.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserUseCaseTest @Autowired constructor(
    private val signUpUseCase: SignUpUseCase,
    private val userRepository: UserRepository,
    private val companyRepository: CompanyRepository,
): DescribeSpec({
    describe("사용자의 회원가입시") {
        val (request, response) = signUp(signUpUseCase)
        context("유저 생성 완료"){
            it("요청한 값과 생성된 값을 비교한다.") {
                response.email shouldBe FULL_EMAIL
                response.name shouldBe NAME
            }
        }

        context("이미 사용중인 이메일이 있다면") {
            signUp(signUpUseCase)
            it("EmailAlreadyExistsException 예외가 발생한다.") {
                shouldThrow<EmailAlreadyExistsException> {
                    signUpUseCase.signUp(request)
                }
            }
        }
    }
}) {
    override suspend fun beforeEach(testCase: TestCase) {
        companyRepository.save(Company.create("구글", DOMAIN))
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        userRepository.deleteAll()
    }
}

private fun signUp(useCase: SignUpUseCase): Pair<SignUpCommand, SignUpResponse> {
    val command = SignUpCommand(EMAIL, DOMAIN, PASSWORD, NAME, COMPANY_ID)
    val user = useCase.signUp(command)
    return Pair(command, user)
}

